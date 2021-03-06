package com.manning.gia.plugins.cloudbees

import spock.lang.Specification
import org.gradle.api.*
import org.gradle.api.plugins.*
import org.gradle.testfixtures.ProjectBuilder

class CloudBeesPluginSpec extends Specification {
	static final APP_INFO_TASK_NAME = 'cloudBeesAppInfo'
	static final APP_DEPLOY_WAR_TASK_NAME = 'cloudBeesAppDeployWar'
	Project project
	   	
	def setup() {
		project = ProjectBuilder.builder().build()
	}
	
	def "Applies plugin and sets extension values"() {
		expect:
			project.tasks.findByName(APP_INFO_TASK_NAME) == null
			project.tasks.findByName(APP_DEPLOY_WAR_TASK_NAME) == null
		when:
			project.apply plugin: 'cloudbees'
			
			project.cloudBees {
				apiKey = 'myKey'
				secret = 'mySecret'
				appId = 'todo'
			}
		then:
		    project.plugins.hasPlugin(WarPlugin)
		    project.extensions.findByName(CloudBeesPlugin.EXTENSION_NAME) != null
		
            Task appInfoTask = project.tasks.findByName(APP_INFO_TASK_NAME)
		    appInfoTask != null
		    appInfoTask.description == 'Returns the basic information about an application.'
		    appInfoTask.group = 'CloudBees'
		    appInfoTask.apiKey = 'myKey'
		    appInfoTask.secret = 'mySecret'
		    appInfoTask.appId = 'todo'
		    Task appDeployWarTask = project.tasks.findByName(APP_DEPLOY_WAR_TASK_NAME)
		    appDeployWarTask != null
		    appDeployWarTask.description == 'Deploys a new version of an application using a WAR file.'
		    appDeployWarTask.group = 'CloudBees'
		    appDeployWarTask.apiKey = 'myKey'
		    appDeployWarTask.secret = 'mySecret'
		    appDeployWarTask.appId = 'todo'
	}
}