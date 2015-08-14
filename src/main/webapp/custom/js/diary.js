(function($, window, document){

	"use strict"

	var DIARY_URL_ROOT = '/iPersonal/dashboard/diaries';

	var Page = Base.extend({

		initialize: function() {
			this.mandatory = {
                'title': true, 'content': true, 'date': true
            },
            this.maxLength = {
                'title': 50, 'content': 3000
            },
            this.formAttributes = ['title', 'content', 'date'];
		}
	});

    var Diary = Backbone.Model.extend({
        urlRoot: DIARY_URL_ROOT
    });

    var Pages = Backbone.Collection.extend({
        model: Page
    });

	var PageView = BaseView.extend({

		el: $('#diary-wrapper'),
        entityType: 'DIARY',
        createTemplate: $('#diary-create-template').html(),
        displayTemplate: $('#diary-display-template').html(),

        events : {
            'click #diary-submit': 'createDiary',
            'click #diary-cancel': 'resetValues',
            'click #diary-tag-img': 'displayTagSelection',
        },

        prepareVariables: function() {
            
            this.saveForm =  $('#diary-form');
            this.tagImage = $('#diary-tag-img');
            this.searchTag = $('#diary-tag');
            this.diaryRTE =  $('#diary');
        },

        resetValues: function(e) {

            if (e) {
                e.preventDefault();
            }
            BaseView.prototype.resetValues.apply(this, arguments);
            this.diaryRTE.empty();
        },

        createDiary: function(e) {

            var self = this;
            e.preventDefault();

            var date = self.saveForm.find('[name=date]').val();
            var content = self.saveForm.find('div#diary').cleanHtml();
            var title = self.saveForm.find('[name=title]').val();

            self.model = new Page();
            self.model.set({
                title: title,
                content: content,
                date: (new Date(self.saveForm.find('[name=date]').val()).getTime() / 1000).toFixed(0)
            });

            var errors = self.model.validate(self.model.attributes);
            if (errors) {
                self.renderErrors(errors);
                return;
            }
            var dateArr = date.split('-');
            self.model.set({
                month: dateArr[1],
                date: dateArr[2]
            });

            var pages = [];
            pages.push(self.model.attributes);
            var diary = new Diary({
                year: dateArr[0],
                pages: pages
            });
        
            var result = diary.save({
                success: function(response) {},
                error: function(error) {}
            });

            if (result) {
                result.complete(function(response){
                    if (response.status != 201) {
                        var errors = self.buildErrorObject(response, self);
                        self.renderErrors(errors);
                    } else {
                        var id = response.responseText;
                        var tags = self.searchTag.val();
                        self.postCreation(id, "DIARY", title, 1, tags);
                        self.model.set({
                            'id': id,
                            'year': dateArr[0],
                            'createdOn': Math.floor(Date.now()),
                            'modifiedAt': Math.floor(Date.now()),
                            'summary': content.replace(/<(?:.|\n)*?>/gm, ''),
                            'dateStr': date,
                            'tags': tags 
                        });
                        self.collection.unshift(self.model);
                        var entityList = self.buildEntityList();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        fetchPages: function(e) {

            var self = this;

            if (e) {
                e.preventDefault();
            }

            if (this.collection.length  == parseInt(entityCountModel.attributes.diaries)) {
                var entityList = this.buildEntityList();
                backboneGlobalObj.trigger('entity:displaylist', entityList);
                return;
            }
             
            this.model = new Diary();   
            this.model.fetch({data: {offset: this.collection.length, limit : 20} }).complete(function(response){
                if (response.status == 200) {
                    var diary = JSON.parse(response.responseText)['diary'];
                    var pages = diary.pages;

                    if (pages instanceof Array) {

                        for (var index in pages) {
                            var page = new Page(pages[index])
                            page.set({ 'year' : diary.year})
                            var date = page.attributes.year + "-" + page.attributes.month + "-" + page.attributes.date;
                            page.set({
                                'dateStr' : date,
                                'id': pages[index]['pageId']
                            })
                            self.collection.push(page);
                        }

                    } else if( pages) {
                        var page = new Page(pages);
                        page.set({ 'year' : diary.year})
                        var date = page.attributes.year + "-" + page.attributes.month + "-" + page.attributes.date;
                        page.set({
                            dateStr : date,
                            'id': pages['pageId']
                        })
                        self.collection.push(page);
                    }

                    var entityList = self.buildEntityList();
                    backboneGlobalObj.trigger('entity:displaylist', entityList);
                }
            });
        },

        buildEntityList: function() {

            var entityList = [];

            for (var i = 0; i < this.collection.length; i++) {
                var summary = this.collection.models[i].attributes.summary;
                var entity = {
                    'entityId' : this.collection.models[i].attributes.id,
                    'entityTitle' : this.collection.models[i].attributes.title,
                    'entitySummary': summary ? summary.substring(0,100) : summary,
                    'entityType': 'diary',
                    'dateStr': this.collection.models[i].attributes.dateStr,
                    'modifiedAt': this.collection.models[i].attributes.modifiedAt,
                };
                entityList.push(entity);
            }
            return entityList;
        },

        getDeletableModel: function(id, year) {

            var model =  new Diary({
                id: id
            });
            model.urlRoot =  '/iPersonal/dashboard/diaries/' + year;
            return model;
        }
	});

	window.pageView = new PageView({ collection: new Pages() });
})(jQuery, window, document);