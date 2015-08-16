(function($, window, document) {

    "use strict"

    var SearchEntity = Base.extend({

        urlRoot: '/iPersonal/dashboard/search'
    });

    var SearchResult = Base.extend({

    });

    var SearchResults = Backbone.Collection.extend({
        model: SearchResult
    });

    var SearchView = BaseView.extend({

        entityType: 'GENERAL',
        el: $('#search-wrapper'),
        generalSearchForm: $('#general-search-form'),

        events: {
            'click #search': 'searchEntities'
        },

        searchEntities: function(e) {

            var self = this;
            e.preventDefault();

            this.model = new SearchEntity();
            var entities = this.generalSearchForm.find('#search-entity').val();
            var tags = this.generalSearchForm.find('#search-tag').val();
            var titles = this.generalSearchForm.find('#search-title').val();
            var keywords = this.generalSearchForm.find('#search-keyword').val();

            if (entities)
                this.model.set({
                    entityTypes: entities
                });
            if (tags)
                this.model.set({
                    tags: tags
                });
            if (titles)
                this.model.set({
                    titles: titles
                });
            if (keywords)
                this.model.set({
                    keywords: keywords
                });

            var result = this.model.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response) {
                    if (response.status == 200) {
                        self.collection = new SearchResults();
                        var searchResults = JSON.parse(response.responseText);

                        if (searchResults) {
                            searchResults = searchResults['searchDocument'];
                            if (searchResults instanceof Array) {

                                for (var index in searchResults) {

                                    var searchResult = new SearchResult();
                                    searchResult.set({
                                        entityType: searchResults[index]['entityType'],
                                        entityId: searchResults[index]['documentId'],
                                        modifiedAt: searchResults[index]['createdAt'],
                                        entityTitle: searchResults[index]['title'],
                                        tags: []
                                    });

                                    if (searchResult.get('entityType') == 'BOOKMARK' || searchResult.get('entityType') == 'PIN') {
                                        searchResult.set({
                                            url: searchResults[index]['summary'].split('###')[0],
                                            entitySummary: searchResults[index]['summary'].split('###')[1]
                                        });
                                    } else {
                                        searchResult.set({
                                            entitySummary: searchResults[index]['summary']
                                        });
                                    }
                                    self.collection.add(searchResult);
                                }

                            } else {
                                var searchResult = new SearchResult({
                                    entityType: searchResults['entityType'],
                                    entityId: searchResults['documentId'],
                                    modifiedAt: searchResults['createdAt'],
                                    entityTitle: searchResults['title'],
                                    tags: []
                                });

                                if (searchResult.get('entityType') == 'BOOKMARK' || searchResult.get('entityType') == 'PIN') {
                                    searchResult.set({
                                        url: searchResults['summary'].split('###')[0],
                                        entitySummary: searchResults['summary'].split('###')[1]
                                    });
                                } else {
                                    searchResult.set({
                                        entitySummary: searchResults['summary']
                                    });
                                }
                                self.collection.add(searchResult);
                            }
                        }
                        var entityList = self.buildEntityList();
                        $('.modal-header').find('button.close').click();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        buildEntityList: function() {

            var entityList = [];

            for (var i = 0; i < this.collection.length; i++) {
                var entity = {};

                entity['entityId'] = this.collection.models[i].attributes.entityId;
                entity['entityType'] = this.collection.models[i].attributes.entityType.toLowerCase();
                entity['entityTitle'] = this.collection.models[i].attributes.entityTitle;
                entity['entitySummary'] = this.collection.models[i].attributes.entitySummary;
                entity['modifiedAt'] = this.collection.models[i].attributes.modifiedAt;

                if (this.collection.models[i].attributes.entityType == 'BOOKMARK' ||
                    this.collection.models[i].attributes.entityType == 'PIN') {
                    entity['url'] = this.collection.models[i].attributes.url;
                }

                entityList.push(entity);
            }
            return entityList;
        },
    });

    window.searchView = new SearchView();

})(jQuery, window, document);