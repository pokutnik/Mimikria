
express = require('express')
routes = require('./routes')
user = require('./routes/user')
http = require('http')
path = require('path')

app = express()

app.configure ->
  app.set 'port', process.env.PORT || 3000
  app.set 'views', __dirname + '/views'
  app.set 'view engine', 'jade'
  app.use express.logger('dev')
  app.use express.bodyParser()
  app.use express.methodOverride()
  app.use express.cookieParser('secret')
  app.use express.session()
  app.use app.router
  app.use require('less-middleware')({ src: __dirname + '/public' })
  app.use express.static(path.join(__dirname, 'public'))

app.configure 'development', ->
  app.use(express.errorHandler())


app.get '/', routes.index
app.get '/users', user.list

app.get '/api/', (req, res) ->
  res.sendfile './public/api/index.json'

app.post '/api/', (req, res) ->
  req.session.email = req.body.email
  res.send req.session



app.get '/api/voices/', (req, res) ->
  res.sendfile './public/api/voices/index.json'

http.createServer(app).listen app.get('port'), ->
  console.log "Express server listening on port " + app.get('port')