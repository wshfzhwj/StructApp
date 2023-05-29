package com.saint.plugin;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainTest {

    public static void main(String[] args) {
        try {
            // 提前准备好要修改的字节码文件
            String classFilePath = "D:/wangsf/androidProject/AsmApp/plugin/build/classes/java/main/com/saint/plugin/Test" +
                    ".class";

            FileInputStream fis = new FileInputStream(classFilePath);
            // 获取分析器
            ClassReader cr = new ClassReader(fis);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            // 开始插桩
            cr.accept(new MyClassVisitor(Opcodes.ASM7, cw), ClassReader.EXPAND_FRAMES);
            // 拿到插桩修改后的字节码
            byte[] bytes = cw.toByteArray();
            // 字节码写回文件
            FileOutputStream fos = new FileOutputStream(classFilePath);
            fos.write(bytes);
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 用来分析类信息
    private static class MyClassVisitor extends ClassVisitor {

        public MyClassVisitor(int api, ClassVisitor classVisitor) {
            super(api, classVisitor);
        }

        // 方法执行会回调该方法
        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
            return new MyMethodVisitor(api, mv, access, name, descriptor);
        }
    }

    // 用来分析方法
    // AdviceAdapter 也是 MethodVisitor，只是功能更多而已
    // 这些字节码插桩处理用 ASM Bytecode Viewer 就能转换出来，不用自己写
    private static class MyMethodVisitor extends AdviceAdapter {
        int s;
        int e;
        boolean inject;

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            if (!inject) return;

            // long s = System.currentTimeMillis();

            // INVOKESTATIC java/lang/System.currentTimeMillis ()J
            // LSTORE 1
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            s = newLocal(Type.LONG_TYPE); // 局部变量表申请空间存放变量
            storeLocal(s);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            if (!inject) return;

            // long e = System.currentTimeMillis();

            // INVOKESTATIC java/lang/System.currentTimeMillis ()J
            // LSTORE 3
            invokeStatic(Type.getType("Ljava/lang/System;"), new Method("currentTimeMillis", "()J"));
            e = newLocal(Type.LONG_TYPE);
            storeLocal(e);

            // System.out.println("execute time = " + (e-s) +"ms");

            // GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
            getStatic(Type.getType("Ljava/lang/System;"), "out", Type.getType("Ljava/io/PrintStream;"));
            // NEW java/lang/StringBuilder
            newInstance(Type.getType("Ljava/lang/StringBuilder;"));
            // DUP
            dup();
            // INVOKESPECIAL java/lang/StringBuilder.<init> ()V
            invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"), new Method("<init>", "()V"));
            // LDC "execute time = "
            visitLdcInsn("execute time = ");
            // INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));

            // LLOAD 3
            loadLocal(e);
            // LLOAD 1
            loadLocal(s);
            // LSUB
            math(GeneratorAdapter.SUB, Type.LONG_TYPE);

            // INVOKEVIRTUAL java/lang/StringBuilder.append (J)Ljava/lang/StringBuilder;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(J)Ljava/lang/StringBuilder;"));
            // LDC "ms"
            visitLdcInsn("ms");
            // INVOKEVIRTUAL java/lang/StringBuilder.append (Ljava/lang/String;)Ljava/lang/StringBuilder;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;"));
            // INVOKEVIRTUAL java/lang/StringBuilder.toString ()Ljava/lang/String;
            invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"), new Method("toString", "()Ljava/lang/String;"));
            // INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"), new Method("println", "(Ljava/lang/String;)V;"));
        }

        // 每读到一个注解就执行一次
        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            // 如果是我们指定的注解 @ASMTest 就进行插桩
            if ("Lcom/saint/plugin/ASMTest;".equals(descriptor)) {
                inject = true;
            }
            return super.visitAnnotation(descriptor, visible);
        }
    }
}
