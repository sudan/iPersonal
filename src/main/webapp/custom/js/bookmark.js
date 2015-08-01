(function($, window, document){

    "use strict"

    var Bookmark = Backbone.Model.extend({
    	urlRoot: '/iPersonal/dashboard/bookmarks',
        validate: function(attributes) {
            var errors = {};

            if (!attributes.name) {
                errors['name'] = 'Title cannot be empty';
            }

            if(!attributes.url) {
                errors['url'] = 'URL cannot be empty';
            }

            if(!attributes.description) {
                errors['description'] = 'Description cannot be empty';
            }
            return $.isEmptyObject(errors) ? false : errors;
        }
    }); 

    var BookmarkView = Backbone.View.extend({

        el: $('#bookmark-wrapper'),

        events : {
            'click #book-submit': 'createBookmark',
            'click #book-cancel': 'resetValues'
        },

        createBookmark: function(e) {

            var self = this;
            e.preventDefault();
            
            var bookmarkForm = $('#bookmark-form');

            var name = bookmarkForm.find('input[name=name]').val();
            var url = bookmarkForm.find('input[name=url]').val();
            var description = bookmarkForm.find('textarea[name=description]').val();

            var bookmark = new Bookmark({
                name: name,
                url: url,
                description: description
            });

            bookmark.on('invalid', function(model , error) {
                self.renderErrors(error, bookmarkForm);
            });

            var result = bookmark.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors, bookmarkForm);
                    } else {
                        self.clearErrors(bookmarkForm);
                    }
                });
            }
        },

        buildErrorObject: function(response, self) {
            window.response = response;
            var errorEntities = JSON.parse(response.responseText)['errorEntity'];

            var errors = {};
            errors['name'] = [];
            errors['url'] = [];
            errors['description'] = [];

            if (errorEntities instanceof Array) {

                for (var i = 0; i < errorEntities.length; i++) {

                    var errorEntity = errorEntities[i];
                    if (errorEntity.field === 'name') {
                        errors['name'].push(errorEntity.description);
                    }
                            
                    if (errorEntity.field === 'url') {
                        errors['url'].push(errorEntity.description);
                    }

                    if (errorEntity.field == 'description') {
                            errors['description'].push(errorEntity.description);
                    }
                }
            } else {
                var errorEntity = errorEntities;
                if (errorEntity.field === 'name') {
                    errors['name'].push(errorEntity.description);
                }
                            
                if (errorEntity.field === 'url') {
                    errors['url'].push(errorEntity.description);
                }

                if (errorEntity.field == 'description') {
                    errors['description'].push(errorEntity.description);
                }
            }

            if (errors.name)
                errors['name'] = errors['name'].join(';');
            if (errors.url)
                errors['url'] = errors['url'].join(';');
            if (errors.description)
                errors['description'] = errors['description'].join(';');

            return errors;
        },

        clearErrors: function(bookmarkForm) {
            bookmarkForm.find('input[name=name]').removeClass('error-field');
            bookmarkForm.find('input[name=url]').removeClass('error-field');
            bookmarkForm.find('textarea[name=description]').removeClass('error-field');

            bookmarkForm.find('.name-error').html('');
            bookmarkForm.find('.url-error').html('');
            bookmarkForm.find('.description-error').html('');
        },

        renderErrors: function(error, bookmarkForm) {
                
                if(error.name) {
                    bookmarkForm.find('input[name=name]').addClass('error-field');
                    bookmarkForm.find('.name-error').html(error.name)
                } else {
                    bookmarkForm.find('input[name=name]').removeClass('error-field');
                    bookmarkForm.find('.name-error').html('');
                }

                if(error.url) {
                    bookmarkForm.find('input[name=url]').addClass('error-field');
                    bookmarkForm.find('.url-error').html(error.url);
                } else {
                    bookmarkForm.find('input[name=url]').removeClass('error-field');
                    bookmarkForm.find('.url-error').html('');
                
                }

                if(error.description) {
                    bookmarkForm.find('textarea[name=description]').addClass('error-field');
                    bookmarkForm.find('.description-error').html(error.description);
                } else {
                    bookmarkForm.find('textarea[name=description]').removeClass('error-field');
                    bookmarkForm.find('.description-error').html('');
                }

        },

        resetValues: function() {

            var self = this;
            e.preventDefault();
            console.log("reseting");
        }
    });

    window.bookmarksView = new BookmarkView();

})(jQuery, window, document);