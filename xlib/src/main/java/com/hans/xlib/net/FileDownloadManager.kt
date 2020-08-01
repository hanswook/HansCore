package com.hans.xlib.net

import android.content.Context
import com.hans.xlib.utils.LogUtils.d
import com.hans.xlib.utils.MD5Util
import com.hans.xlib.utils.RxUtil
import com.hans.xlib.utils.StringUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.io.*
import java.util.regex.Pattern

/**
 * @date: 2019-12-04 16:44
 * @author: drq
 * @description WebCache缓存管理类
 * 通过拦截资源下载，存到本地，然后遇到重复请求时直接读取本地资源来节省流量
 */
class FileDownloadManager {
    fun downloadFile(
        context: Context,
        url: String,
        downloadCallback: DownloadCallback
    ) {
        var suffix = ""
        try {
            suffix = parseSuffix(url)
            d("downloadFile suffix parseSuffix:$suffix")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (StringUtils.isValid(suffix) && !suffix.contains(".")) {
            suffix = ".$suffix"
        }
        suffix = StringUtils.trimToEmpty(suffix)
        d("downloadFile suffix:$suffix")
        val path = context.externalCacheDir
            ?.getAbsolutePath() + File.separator + "videocache" + File.separator + MD5Util.md5String(
            url
        ) + suffix
        val now = File(path)
        if (now.exists()) {
            d("downloadFile now.exists()")
            downloadCallback.success(now.absolutePath)
            return
        }
        WebRepository.getInstance()
            .webDownloadService
            .rxDownload(url)
            .map<File> { responseBodyResponse ->
                d("onNext")
                if (!now.parentFile.exists()) {
                    now.parentFile.mkdir()
                }
                writeFileFromIS(path, responseBodyResponse.body()!!.byteStream())
            }
            .compose<File>(RxUtil.applySchedulers())
            .subscribe(object : Observer<File?> {
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(file: File) {
                    if (file == null) {
                        d("downloadCallback.failed()")
                        downloadCallback.failed()
                    } else {
                        d("downloadCallback.success")
                        downloadCallback.success(file.absolutePath)
                    }
                }

                override fun onError(e: Throwable) {
                    d("onError")
                    e.printStackTrace()
                    downloadCallback.failed()
                }

                override fun onComplete() {}
            })
    }

    private val sBufferSize = 8192

    /**
     * 将输入流写入文件
     *
     * @param filePath
     * @param is
     * @return
     */
    private fun writeFileFromIS(
        filePath: String,
        `is`: InputStream?
    ): File? {
        d("download start:" + System.currentTimeMillis())
        val file = File("$filePath.temp")
        if (`is` == null) {
            return null
        }
        //创建文件
        if (!file.exists()) {
            val parentFile = file.parentFile
            if (!parentFile.exists()) parentFile.mkdir()
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        var os: OutputStream? = null
        try {
            os = BufferedOutputStream(FileOutputStream(file))
            val data = ByteArray(sBufferSize)
            var len: Int
            while (`is`.read(data, 0, sBufferSize).also { len = it } != -1) {
                os.write(data, 0, len)
            }
        } catch (e: IOException) {
            d("download IOException e:$e")
            e.printStackTrace()
            if (file.exists()) {
                file.delete()
            }
        } finally {
            try {
                `is`.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                os?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            d("download end")
        }
        var renameFile: File? = null
        try {
            renameFile = File(filePath)
            file.renameTo(renameFile)
        } catch (ex1: Exception) {
            ex1.printStackTrace()
        }
        return renameFile
    }

    companion object {
        private var sInstace: FileDownloadManager? = null

        /**
         * 创建单例
         */
        val instance: FileDownloadManager?
            get() {
                if (sInstace == null) {
                    synchronized(
                        FileDownloadManager::class.java
                    ) { sInstace = FileDownloadManager() }
                }
                return sInstace
            }

        val pattern = Pattern.compile("\\S*[?]\\S*")

        /**
         * 获取链接的后缀名
         *
         * @return
         */
        private fun parseSuffix(url: String): String {
            val matcher =
                pattern.matcher(url)
            val spUrl =
                url.split("/".toRegex()).toTypedArray()
            val len = spUrl.size
            val endUrl = spUrl[len - 1]
            if (matcher.find()) {
                val spEndUrl =
                    endUrl.split("\\?".toRegex()).toTypedArray()
                return spEndUrl[0].split("\\.".toRegex()).toTypedArray()[1]
            }
            return endUrl.split("\\.".toRegex()).toTypedArray()[1]
        }
    }
}