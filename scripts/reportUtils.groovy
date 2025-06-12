def generateHtmlReport(def results) {
    def rows = results.collect { result ->
        """
        <tr>
            <td>${result.collection.info.name}</td>
            <td>${result.environment.name}</td>
            <td>${result.run.stats.requests.total}</td>
            <td>${result.run.stats.requests.failed}</td>
        </tr>
        """
    }.join('\n')

    return """
    <html>
        <head>
            <style>
                table { border-collapse: collapse; width: 100%; }
                th, td { border: 1px solid black; padding: 8px; text-align: left; }
                th { background-color: #f2f2f2; }
            </style>
        </head>
        <body>
            <h2>Postman Test Results</h2>
            <table>
                <thead>
                    <tr>
                        <th>Collection</th>
                        <th>Environment</th>
                        <th>Total Requests</th>
                        <th>Failed Requests</th>
                    </tr>
                </thead>
                <tbody>
                    ${rows}
                </tbody>
            </table>
        </body>
    </html>
    """
}

return this
