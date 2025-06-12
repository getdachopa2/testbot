def getPostmanFiles(String pattern) {
    def files = findFiles(glob: pattern)
    return files.collect { it.name }.join(',')
}

return this
