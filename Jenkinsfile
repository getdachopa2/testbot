// Jenkinsfile

// --- PIPELINE TANIMI ---
pipeline {
    agent any

    parameters {
        string(name: 'EMAIL_RECIPIENT', defaultValue: 'example@example.com', description: 'Recipient email for the report')
        string(name: 'CHANNEL_ID', defaultValue: '', description: 'Channel ID (leave empty to use default from environment)')
        string(name: 'APPLICATION', defaultValue: '', description: 'Application name (leave empty to use default from environment)')
        string(name: 'PASSWORD', defaultValue: '', description: 'Password (leave empty to use default from environment)')
        choice(name: 'POSTMAN_ENVIRONMENTS', choices: getPostmanFiles('*.postman_environment.json'), description: 'Comma-separated list of environments to run')
        choice(name: 'POSTMAN_COLLECTIONS', choices: getPostmanFiles('*.postman_collection.json'), description: 'Comma-separated list of collections to run')
    }

    tools {
        nodejs 'NodeJS-LTS'
    }

    stages {
        stage('Setup') {
            steps {
                script {
                    setupEnvironment()
                }
            }
        }

        stage('Run Collections') {
            steps {
                script {
                    def results = runCollections(params.POSTMAN_COLLECTIONS, params.POSTMAN_ENVIRONMENTS)
                    writeJSON file: 'results.json', json: results
                }
            }
        }

        stage('Generate Report') {
            steps {
                script {
                    def results = readJSON file: 'results.json'
                    def reportHtml = generateHtmlReport(results)
                    writeFile file: 'report.html', text: reportHtml
                }
            }
        }

        stage('Send Email') {
            steps {
                script {
                    sendEmail(params.EMAIL_RECIPIENT, 'report.html')
                }
            }
        }
    }
}

// Load reusable methods from external Groovy script
def getPostmanFiles(String pattern) {
    def postmanUtils = load 'scripts/postmanUtils.groovy'
    return postmanUtils.getPostmanFiles(pattern)
}

def setupEnvironment() {
    load 'scripts/setupUtils.groovy'.setupEnvironment()
}

def runCollections(String collections, String environments) {
    return load 'scripts/collectionRunner.groovy'.runCollections(collections, environments)
}

def generateHtmlReport(def results) {
    return load 'scripts/reportUtils.groovy'.generateHtmlReport(results)
}

def sendEmail(String recipient, String reportFile) {
    load 'scripts/emailUtils.groovy'.sendEmail(recipient, reportFile)
}
