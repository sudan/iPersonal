(function($, window, document){

    "use strict"

    var BOOKMARK_URL_ROOT = '/iPersonal/dashboard/bookmarks';

    var Bookmark = Base.extend({
    	urlRoot: BOOKMARK_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'name': true, 'url': true, 'description': true
            },
            this.maxLength = {
                'name': 50, 'url': 150, 'description': 1000
            },
            this.formAttributes = ['name', 'url', 'description']
        }

    }); 

    var Bookmarks = Backbone.Collection.extend({
        model: Bookmark
    });

    var BookmarkView = BaseView.extend({

        el: $('#bookmark-wrapper'),
        createTemplate: $('#bookmark-create-template').html(),

        events : {
            'click #book-submit': 'createBookmark',
            'click #book-cancel': 'resetValues',
            'click #book-tag-img': 'displayTagSelection',
        },

        initialize: function() {
            this.model = new Bookmark();
        },

        prepareVariables: function() {
            
            this.saveForm =  $('#bookmark-form');
            this.tagImage = $('#book-tag-img');
            this.searchTag = $('#bookmark-tag');
        },

        createBookmark: function(e) {

            var self = this;
            e.preventDefault();

            self.model.set({
                name: self.saveForm.find('[name=name]').val(),
                url: self.saveForm.find('[name=url]').val(),
                description: self.saveForm.find('[name=description]').val()
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
                        self.postCreation(bookmarkId, "BOOKMARK", self.model.get('name'), 1, tags)
                        self.model.set({
                            'createdOn': Math.floor(Date.now()/1000),
                            'modifiedAt': Math.floor(Date.now()/1000),
                            'tags': tags 
                        });
                        self.collection.unshift(self.model);
                    }
                });
            }
        },

        fetchBookmarks: function(e) {

            var offset = 0;
            var self = this;

            if (e) {
                e.preventDefault();

                if ($(e.target).attr('offset')) {
                    offset = $(e.target).attr('offset');
                }
            }

            if (this.collection.length  >= parseInt(offset+1) * 10)
                return;

            this.model.fetch({data: {offset: offset, limit : 20} }).complete(function(response){
                if (response.status == 200) {
                        var bookmarks = JSON.parse(response.responseText)['bookmark'];
                        for (var index in bookmarks) {
                            self.collection.push(bookmarks[index]);
                        }
                }
            });
        },

        renderAll: function() {
            
        }
    });

    window.bookmarkView = new BookmarkView({ collection : []});

})(jQuery, window, document);