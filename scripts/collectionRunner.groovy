def runCollections(String collections, String environments) {
    def results = []
    def collectionList = collections.split(',')
    def environmentList = environments.split(',')

    collectionList.each { collection ->
        environmentList.each { environment ->
            def reportFile = "${collection.trim()}_${environment.trim()}_report.json"
            sh "newman run ${collection.trim()} -e ${environment.trim()} -r json --reporter-json-export ${reportFile}"
            results.add(readJSON file: reportFile)
        }
    }

    return results
}

return this
