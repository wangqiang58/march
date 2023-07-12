package com.xdf.plugin.bitmap

import com.xdf.plugin.base.BaseTransform
import com.xdf.plugin.utils.Log
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

//本地大图监控
class LargeBitmapTransform : BaseTransform() {

    val config: LargeBitmapConfig = LargeBitmapConfig()


    private companion object {

        private const val ImageViewClass = "android/widget/ImageView"

        private const val AppCompatImageViewClass = "androidx/appcompat/widget/AppCompatImageView"

    }

    override fun modifyClass(byteArray: ByteArray): ByteArray {
        val classReader = ClassReader(byteArray)
        val className = classReader.className
        val superName = classReader.superName
        Log.log("className: $className superName: $superName")


        return if ((superName == ImageViewClass && className != config.formatMonitorImageViewClass) || className == AppCompatImageViewClass) {
            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
            val classVisitor = object : ClassVisitor(Opcodes.ASM4, classWriter) {
                override fun visit(
                        version: Int,
                        access: Int,
                        name: String?,
                        signature: String?,
                        superName: String?,
                        interfaces: Array<out String>?
                ) {
                    super.visit(
                            version,
                            access,
                            name,
                            signature,
                            config.formatMonitorImageViewClass,
                            interfaces
                    )
                }
            }
            classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES)
            classWriter.toByteArray()
        } else {
            byteArray
        }
    }

    override fun getTransformName(): String {
        return "LargeBitmapTransform"
    }
}