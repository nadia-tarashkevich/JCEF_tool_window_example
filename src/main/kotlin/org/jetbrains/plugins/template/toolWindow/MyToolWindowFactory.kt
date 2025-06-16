package org.jetbrains.plugins.template.toolWindow

import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.components.JBPanel
import java.awt.BorderLayout
import javax.swing.JPanel

class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        // Create a JCEF browser
        private val browser = JBCefBrowser()

        init {
            // Load HTML content with markdown-style list and hyperlinks to Google
            val htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            margin: 20px;
                        }
                        ul {
                            list-style-type: disc;
                            padding-left: 20px;
                        }
                        li {
                            margin-bottom: 10px;
                        }
                        a {
                            color: #4285F4;
                            text-decoration: none;
                        }
                        a:hover {
                            text-decoration: underline;
                        }
                    </style>
                </head>
                <body>
                    <ul>
                        <li><a href="https://www.google.com">List item 1</a></li>
                        <li><a href="https://www.google.com">List item 2</a></li>
                    </ul>
                </body>
                </html>
            """.trimIndent()

            browser.loadHTML(htmlContent)
        }

        fun getContent(): JPanel {
            val panel = JBPanel<JBPanel<*>>(BorderLayout())
            panel.add(browser.component, BorderLayout.CENTER)
            return panel
        }
    }
}
