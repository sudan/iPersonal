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
            },
            this.formAttributes = ['name', 'url', 'description']
        }

    }); 

    var BookmarkView = BaseView.extend({

        el: $('#bookmark-wrapper'),
        saveForm: $('#bookmark-form'),

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
            
            var name = self.saveForm.find('input[name=name]').val();
            var url = self.saveForm.find('input[name=url]').val();
            var description = self.saveForm.find('textarea[name=description]').val();

            self.model = new Bookmark({
                name: name,
                url: url,
                description: description
            });

            self.model.on('invalid', function(model , error) {
                self.renderErrors(error);
            });

            var result = self.model.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors);
                    } else {
                        var bookmarkId = response.responseText;
                        self.model.set({ bookmarkId: bookmarkId});
                        var tags = self.searchTag.val();
                        if (tags) {
                            backboneGlobalObj.trigger('tag:add', {
                                'entityId': bookmarkId,
                                'entityType': 'BOOKMARK',
                                'entityTitle': self.model.get(BOOKMARK_NAME),
                                'tags': tags
                            });
                        }
                        backboneGlobalObj.trigger('entity:count', {
                            'entityType': 'BOOKMARK',
                            'relativeValue': 1
                        });
                        self.clearErrors();
                        // Add it to collection in future
                        self.model.clear();
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

        clearErrors: function() {
            this.saveForm.find('input[name=name]').removeClass('error-field');
            this.saveForm.find('input[name=url]').removeClass('error-field');
            this.saveForm.find('textarea[name=description]').removeClass('error-field');

            this.saveForm.find('.name-error').html('');
            this.saveForm.find('.url-error').html('');
            this.saveForm.find('.description-error').html('');
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
        }

    });

    window.bookmarksView = new BookmarkView();

})(jQuery, window, document);