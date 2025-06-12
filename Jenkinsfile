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
        stage('Checkout') {
            steps {
                checkout scm
                script {
                    sh 'pwd' // Print the current working directory
                    sh 'ls -R' // List all files and directories recursively
                    sh 'ls scripts' // List the contents of the scripts directory
                }
            }
        }

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
    def postmanUtils
    node {
        postmanUtils = load 'scripts/postmanUtils.groovy'
    }
    return postmanUtils.getPostmanFiles(pattern)
}

def setupEnvironment() {
    def setupUtils
    node {
        setupUtils = load 'scripts/setupUtils.groovy'
    }
    setupUtils.setupEnvironment()
}

def runCollections(String collections, String environments) {
    def collectionRunner
    node {
        collectionRunner = load 'scripts/collectionRunner.groovy'
    }
    return collectionRunner.runCollections(collections, environments)
}

def generateHtmlReport(def results) {
    def reportUtils
    node {
        reportUtils = load 'scripts/reportUtils.groovy'
    }
    return reportUtils.generateHtmlReport(results)
}

def sendEmail(String recipient, String reportFile) {
    def emailUtils
    node {
        emailUtils = load 'scripts/emailUtils.groovy'
    }
    emailUtils.sendEmail(recipient, reportFile)
}
