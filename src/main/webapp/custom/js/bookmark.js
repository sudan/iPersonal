(function($, window, document){

    "use strict"

    var BOOKMARK_URL_ROOT = '/iPersonal/dashboard/bookmarks';
    var BOOKMARK_NAME = 'name';
    var BOOKMARK_URL = 'url';
    var BOOKMARK_DESCRIPTION = 'description';

    var Bookmark = Backbone.Model.extend({
    	urlRoot: BOOKMARK_URL_ROOT,
        validate: function(attributes) {
            var errors = {};

            if (!attributes.name) {
                errors[BOOKMARK_NAME] = 'Title cannot be empty';
            }

            if(!attributes.url) {
                errors[BOOKMARK_URL] = 'URL cannot be empty';
            }

            if(!attributes.description) {
                errors[BOOKMARK_DESCRIPTION] = 'Description cannot be empty';
            }
            return $.isEmptyObject(errors) ? false : errors;
        }
    }); 

    var BookmarkView = Backbone.View.extend({

        el: $('#bookmark-wrapper'),

        events : {
            'click #book-submit': 'createBookmark',
            'click #book-cancel': 'resetValues',
            'click #book-tag-img': 'displayTagSelection',
            'click #book-enable-new-tag': 'renderTagInput',
            'click #book-enable-existing-tag': 'renderTagInput'
        },

        tagImage: $('#book-tag-img'),
        searchTag: $('#book-existing-tag'),
        addTag: $('#book-new-tag'),
        enableSearchTag: $('#book-enable-existing-tag'),
        enableAddTag: $('#book-enable-new-tag'),

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
            errors[BOOKMARK_NAME] = [];
            errors[BOOKMARK_URL] = [];
            errors[BOOKMARK_DESCRIPTION] = [];

            if (errorEntities instanceof Array) {

                for (var i = 0; i < errorEntities.length; i++) {

                    var errorEntity = errorEntities[i];
                    if (errorEntity.field === BOOKMARK_NAME) {
                        errors[BOOKMARK_NAME].push(errorEntity.description);
                    }
                            
                    if (errorEntity.field === BOOKMARK_URL) {
                        errors[BOOKMARK_URL].push(errorEntity.description);
                    }

                    if (errorEntity.field == BOOKMARK_DESCRIPTION) {
                            errors[BOOKMARK_DESCRIPTION].push(errorEntity.description);
                    }
                }
            } else {
                var errorEntity = errorEntities;
                if (errorEntity.field === BOOKMARK_NAME) {
                    errors[BOOKMARK_NAME].push(errorEntity.description);
                }
                            
                if (errorEntity.field === BOOKMARK_URL) {
                    errors[BOOKMARK_URL].push(errorEntity.description);
                }

                if (errorEntity.field == BOOKMARK_DESCRIPTION) {
                    errors[BOOKMARK_DESCRIPTION].push(errorEntity.description);
                }
            }

            if (errors.name)
                errors[BOOKMARK_NAME] = errors[BOOKMARK_NAME].join(';');
            if (errors.url)
                errors[BOOKMARK_URL] = errors[BOOKMARK_URL].join(';');
            if (errors.description)
                errors[BOOKMARK_DESCRIPTION] = errors[BOOKMARK_DESCRIPTION].join(';');

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

        displayTagSelection: function(e) {
            
            var self = this;
            self.tagImage.addClass('invisible');

            self.searchTag.parent('div.form-group').removeClass('invisible');
            self.enableAddTag.parent('div.form-group').removeClass('invisible');
        },

        resetValues: function(e) {

            var self = this;
            e.preventDefault();
            
            var bookmarkForm = $('#bookmark-form');

            bookmarkForm.find('input[name=name]').val('');
            bookmarkForm.find('input[name=url]').val('');
            bookmarkForm.find('textarea[name=description]').val('');

            self.tagImage.removeClass('invisible');

            self.searchTag.parent('div.form-group').addClass('invisible');
            self.addTag.parent('div.form-group').addClass('invisible');
            self.enableAddTag.parent('div.form-group').addClass('invisible');
        },

        renderTagInput: function(e) {

            var self = this;
            e.preventDefault();

            var selectionId = $(e.target).attr('id');

            if (selectionId ===  'book-enable-new-tag') {
                self.searchTag.parent('div.form-group').addClass('invisible');
                self.addTag.parent('div.form-group').removeClass('invisible');
            } else if (selectionId == 'book-enable-existing-tag') {
                self.searchTag.parent('div.form-group').removeClass('invisible');
                self.addTag.parent('div.form-group').addClass('invisible');
            }
        }

    });

    window.bookmarksView = new BookmarkView();

})(jQuery, window, document);