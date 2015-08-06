(function($, window, document){

    "use strict"

    var BOOKMARK_URL_ROOT = '/iPersonal/dashboard/bookmarks';
    var BOOKMARK_NAME = 'name';
    var BOOKMARK_URL = 'url';
    var BOOKMARK_DESCRIPTION = 'description';

    var Bookmark = Base.extend({
    	urlRoot: BOOKMARK_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'name': true,
                'url': true,
                'description': true
            },
            this.maxLength = {
                'name': 50,
                'url': 150,
                'description': 1000
            }
        }

    }); 

    var BookmarkView = Backbone.View.extend({

        el: $('#bookmark-wrapper'),

        events : {
            'click #book-submit': 'createBookmark',
            'click #book-cancel': 'resetValues',
            'click #book-tag-img': 'displayTagSelection',
        },

        tagImage: $('#book-tag-img'),
        searchTag: $('#book-tag'),

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
                        var bookmarkId = response.responseText;
                        bookmark.set({ bookmarkId: bookmarkId});
                        var tags = self.searchTag.val();
                        if (tags) {
                            backboneGlobalObj.trigger('tag:add', {
                                'entityId': bookmarkId,
                                'entityType': 'BOOKMARK',
                                'entityTitle': bookmark.get(BOOKMARK_NAME),
                                'tags': tags
                            });
                        }
                        backboneGlobalObj.trigger('entity:count', {
                            'entityType': 'BOOKMARK',
                            'relativeValue': 1
                        });
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

            window.currentTagEntity = self.searchTag;
            self.searchTag.parent('div.form-group').removeClass('invisible');

            var tags = window.tagModel.getTags();
            if(tags) {
                for (var i = 0; i < tags.length; i++) {
                    self.searchTag.append($('<option></option>').attr('value', tags[i]).text(tags[i]));
                }
                self.searchTag.trigger('chosen:updated');
            }
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
        }

    });

    window.bookmarksView = new BookmarkView();

})(jQuery, window, document);