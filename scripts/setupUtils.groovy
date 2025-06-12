def setupEnvironment() {
    sh 'npm install -g newman newman-reporter-htmlextra'
}

return this
