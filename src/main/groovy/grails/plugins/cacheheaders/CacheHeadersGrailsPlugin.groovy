package grails.plugins.cacheheaders

import grails.plugins.Plugin
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j

@Slf4j
@CompileStatic
class CacheHeadersGrailsPlugin extends Plugin {
	def grailsVersion = "3.0 > *"
	def observe = ['controllers']
	def pluginExcludes = [
		"**/TestController**",
		"src/docs/**"
	]
	def author = "Graeme Rocher"
	def authorEmail = "grocher@gopivotal.com"
	def title = "Caching Headers Plugin"
	def description = 'Improve your application performance with browser caching, with easy ways to set caching headers in controller responses'
	def developers = [ [ name: "Marc Palmer", email: "marc@grailsrocks.com" ], [ name: "Graeme Rocher", email: "grocher@gopivotal.com" ]]
	def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPCACHEHEADERS" ]
	def scm = [ url: "http://github.com/grails-plugins/grails-cache-headers" ]
	def license = "APACHE"
	def documentation = "http://grails.org/plugin/cache-headers"

    void doWithApplicationContext() {
        CacheHeadersService cacheHeadersService = applicationContext.getBean('cacheHeadersService', CacheHeadersService)
        cacheHeadersService.enabled = config.getProperty('cache.headers.enabled', Boolean, true)
        cacheHeadersService.presets = getPresets(config)
        log.info "Caching enabled in Config: ${cacheHeadersService.enabled}"
        log.debug "Caching presets declared: ${cacheHeadersService.presets}"
    }

    @CompileDynamic
    static Map getPresets(config) {
        config.cache.headers.presets
    }

    void onConfigChange(Map<String, Object> event) {
        // Config change might mean that the caching has been turned on/off
        doWithApplicationContext()
    }
}
