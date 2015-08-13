(function($, window, document){

    "use strict"

    var BOOKMARK_URL_ROOT = '/iPersonal/dashboard/bookmarks';

    var Bookmark = Base.extend({
    	urlRoot: BOOKMARK_URL_ROOT,
        
        defaults: {
            tags: []
        },
        
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

        entityType: 'BOOKMARK',
        el: $('#bookmark-wrapper'),
        createTemplate: $('#bookmark-create-template').html(),
        displayTemplate: $('#bookmark-display-template').html(),

        events : {
            'click #book-submit': 'createBookmark',
            'click #book-cancel': 'resetValues',
            'click #book-tag-img': 'displayTagSelection'
        },

        initialize: function() {

            var self = this;
        },

        prepareVariables: function() {
            
            this.saveForm =  $('#bookmark-form');
            this.tagImage = $('#book-tag-img');
            this.searchTag = $('#bookmark-tag');
        },

        createBookmark: function(e) {

            var self = this;
            e.preventDefault();

            self.model = new Bookmark();

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
                        var tags = self.searchTag.val();
                        self.postCreation(bookmarkId, "BOOKMARK", self.model.get('name'), 1, tags)
                        self.model.set({
                            bookmarkId: bookmarkId,
                            'createdOn': Math.floor(Date.now()),
                            'modifiedAt': Math.floor(Date.now()),
                            'tags': tags 
                        });
                        self.collection.unshift(self.model);
                        var entityList = self.buildEntityList();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        fetchBookmarks: function(e) {

            var self = this;

            if (e) {
                e.preventDefault();
            }

            if (this.collection.length  == parseInt(entityCountModel.attributes.bookmarks)) {
                var entityList = this.buildEntityList();
                backboneGlobalObj.trigger('entity:displaylist', entityList);
                return;
            }
             
            this.model = new Bookmark();   
            this.model.fetch({data: {offset: this.collection.length, limit : 20} }).complete(function(response){
                if (response.status == 200) {
                    var bookmarks = JSON.parse(response.responseText)['bookmark'];
                    if (bookmarks instanceof Array) {
                        for (var index in bookmarks) {
                            var bookmark = new Bookmark(bookmarks[index]);
                            if (bookmark.attributes.tags && typeof bookmark.attributes.tags === "string") {
                                var tags = []
                                tags.push(bookmark.attributes.tags);
                                bookmark.set({ tags : tags});
                            }
                            self.collection.push(bookmark);
                        }
                    } else if (bookmarks) {
                        var bookmark = new Bookmark(bookmarks);
                        if (bookmark.attributes.tags && typeof bookmark.attributes.tags === "string") {
                            var tags = []
                            tags.push(bookmark.attributes.tags);
                            bookmark.set({ tags : tags});
                        }
                        self.collection.push(bookmark);
                    }
                    var entityList = self.buildEntityList();
                    backboneGlobalObj.trigger('entity:displaylist', entityList);
                }
            });
        },

        buildEntityList: function() {

            var entityList = [];

            for (var i = 0; i < this.collection.length; i++) {
                var description = this.collection.models[i].attributes.description;
                var entity = {
                    'entityId' : this.collection.models[i].attributes.bookmarkId,
                    'entityTitle' : this.collection.models[i].attributes.name,
                    'url': this.collection.models[i].attributes.url,
                    'entitySummary': description ? description.substring(0,100) : description,
                    'entityType': 'bookmark',
                    'modifiedAt': this.collection.models[i].attributes.modifiedAt,
                };
                entityList.push(entity);
            }
            return entityList;
        },

        getDeletableModel: function(bookmarkId) {

            return new Bookmark({
                id: bookmarkId
            });
        },

        getModel: function(bookmarkId) {

            if (bookmarkId) {
                var index = this.findIndex(bookmarkId);
                return this.collection.at(index);
            } else {
                return new Bookmark({
                    bookmarkId: "",
                    name: "",
                    description: "",
                    url: "",
                    tags: []
                });
            }
        },

        findIndex: function(bookmarkId) {
            for (var i = 0; i < this.collection.models.length; i++) {
                if (this.collection.models[i].attributes.bookmarkId == bookmarkId) {
                    break;
                }
            }
            return i;
        },

        initEntity: function() {
            Init.initBookmark();
        }
    });

    window.bookmarkView = new BookmarkView({ collection : new Bookmarks()});

})(jQuery, window, document);