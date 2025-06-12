def sendEmail(String recipient, String reportFile) {
    emailext(
        subject: "[Jenkins] Postman Test Results",
        body: "Please find the attached test report.",
        to: recipient,
        mimeType: 'text/html',
        attachLog: true,
        attachmentsPattern: reportFile
    )
}

return this
