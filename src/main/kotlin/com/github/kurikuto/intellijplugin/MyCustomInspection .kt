package com.github.kurikuto.intellijplugin

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.JavaElementVisitor
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiMethod

class MyCustomInspection : LocalInspectionTool() {

    // Settings | Editor | Inspections に表示される名前
    override fun getDisplayName(): String = "Method name should not be 'foo'"

    // Inspections の詳細表示（説明文）
    override fun getStaticDescription(): String = "This inspection warns when a method is named 'foo'."

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
        // JavaElementVisitor を使うことで visitMethod が使える
        return object : JavaElementVisitor() {
            override fun visitMethod(method: PsiMethod) {
                super.visitMethod(method)
                // 名前が "foo" なら警告
                if (method.name == "foo") {
                    // nameIdentifier は nullable なので安全に扱う
                    val nameId = method.nameIdentifier ?: return
                    holder.registerProblem(nameId, "Method name should not be 'foo'")
                }
            }
        }
    }
}

