fs = require 'fs'

module.exports = {
  loadjson: (path) ->
    data = fs.readFileSync path, 'utf8'
    JSON.parse(data)
}
