var gulp = require("gulp"),
    gutil = require('gulp-util'),
    sass = require('gulp-sass'),
    sourcemaps = require('gulp-sourcemaps'),
    autoprefixer = require('gulp-autoprefixer'),
    browserSync = require('browser-sync').create(),
    reload = browserSync.reload,
    clean_css = require('gulp-clean-css');

var sassOptions = {
    errLogToConsole: true,
    outputStyle: 'expanded'
};

// var paths = required("./gulp_paths.json");

gulp.task('sass', function() {
  return gulp
    .src("_src/sass/main.sass")
    .pipe(sourcemaps.init())
    .pipe(sass(sassOptions).on('error', sass.logError))
    // .pipe(autoprefixer())
    // .pipe(clean_css({level: 2, compatibility: 'ie9'}))
    .pipe(sourcemaps.write('maps'))
    .pipe(gulp.dest('_site/assets/css'));
});

gulp.task('js', function() {
  return gulp
    .src("_src/js/script.js")
    .pipe(sourcemaps.init())
    .pipe(gulp.dest('_site/assets/js'));
})

gulp.task('copy_plugins', ['sass'], function() {
  // gulp.src([
  //   paths.jquery.src,
  //   paths.foundation.src.js
  // ]).pipe(gulp.dest(paths.js.dest));

  // gulp.src([
  //   paths.foundation.src.css
  // ]).pipe(gulp.dest(paths.css.dest));

  gulp.src([
    "_site/assets/css/main.css"
  ]).pipe(gulp.dest(paths.css.dest));

});


gulp.task('serve', function() {
    browserSync.init({
      server: {
        baseDir: "./"
      }
    });

    gulp.watch("_src/sass/**/*.sass", ['sass']);
    gulp.watch("_site/assets/css/main.css").on('change', reload);
    gulp.watch("_src/js/**/*.js", ['js']);
    gulp.watch("_site/assets/js/**/*.js").on('change', reload);
    gulp.watch(paths.html.watch, ['html-watch']);
    gulp.watch("index.html").on('change', reload);
});

gulp.task('default', ['serve']);
