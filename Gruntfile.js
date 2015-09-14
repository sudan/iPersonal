module.exports = function(grunt) {

	grunt.initConfig({
		uglify: {
			my_target: {
				files: {
					'src/main/webapp/custom/dist/custom.min.js': [
						'src/main/webapp/custom/js/tag.js',
						'src/main/webapp/custom/js/expense_category.js',
						'src/main/webapp/custom/js/init.js',
						'src/main/webapp/custom/js/user.js',
						'src/main/webapp/custom/js/base.js',
						'src/main/webapp/custom/js/bookmark.js',
						'src/main/webapp/custom/js/note.js',
						'src/main/webapp/custom/js/pin.js',
						'src/main/webapp/custom/js/todo.js',
						'src/main/webapp/custom/js/diary.js',
						'src/main/webapp/custom/js/expense.js',
						'src/main/webapp/custom/js/entity.js',
						'src/main/webapp/custom/js/entity_list.js',
						'src/main/webapp/custom/js/search.js'
					],
					'src/main/webapp/vendor/dist/vendor.min.js': [
						'src/main/webapp/vendor/jquery/dist/jquery.js',
						'src/main/webapp/vendor/bootstrap/dist/js/bootstrap.js',
						'src/main/webapp/vendor/metisMenu/dist/metisMenu.js',
						'src/main/webapp/vendor/dist/js/sb-admin-2.js',
						'src/main/webapp/vendor/underscore/dist/underscore.js',
						'src/main/webapp/vendor/backbone/dist/backbone.js',
						'src/main/webapp/vendor/chosen/dist/chosen.js',
						'src/main/webapp/vendor/datepicker/dist/datepicker.js',
						'src/main/webapp/vendor/rte/dist/jquery.hotkeys.js',
						'src/main/webapp/vendor/rte/dist/prettify.js',
						'src/main/webapp/vendor/rte/dist/bootstrap-wysiwyg.js',
						'src/main/webapp/vendor/moment/dist/moment.js',
						'src/main/webapp/vendor/confirm/dist/bootstrap-tooltip.js',
						'src/main/webapp/vendor/confirm/dist/bootstrap-confirmation.js'
					]
				}
			}
		}
	});
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.registerTask('default', ['uglify']);
};