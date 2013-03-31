fs = require 'fs'

module.exports = {
  loadjson: (path) ->
    data = fs.readFileSync path, 'utf8'
    JSON.parse data

  sessionLogging: (req, res, next) ->
      console.log '%s %s', req.method, req.url
      console.log req.session
      next()
}
