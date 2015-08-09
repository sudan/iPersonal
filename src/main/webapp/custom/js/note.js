(function($, window, document){

    "use strict"

    var NOTE_URL_ROOT = '/iPersonal/dashboard/notes';

    var Note = Base.extend({
    	urlRoot: NOTE_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'title': true, 'note': true
            },
            this.maxLength = {
                'title': 50,  'note': 1000
            },
            this.formAttributes = ['title', 'note']
        }

    });

    var Notes = Backbone.Collection.extend({
        model: Note
    });

    var NoteView = BaseView.extend({

        el: $('#note-wrapper'),
        createTemplate: $('#note-create-template').html(),

        events : {
            'click #note-submit': 'createNote',
            'click #note-cancel': 'resetValues',
            'click #note-tag-img': 'displayTagSelection',
        },

        prepareVariables: function() {

            this.saveForm = $('#note-form');
            this.tagImage = $('#note-tag-img');
            this.searchTag =  $('#note-tag');
            this.noteRTE =  $('#note');
        },

        resetValues: function(e) {

            if (e) {
                e.preventDefault();
            }
            BaseView.prototype.resetValues.apply(this, arguments);
            this.noteRTE.empty();
        },

        createNote: function(e) {

            var self = this;
            e.preventDefault();

            self.model = new Note();

            self.model.set({
                title: self.saveForm.find('[name=title]').val(),
                note: self.saveForm.find('div#note').cleanHtml(),
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
                        var noteId = response.responseText;
                        self.model.set({ noteId: noteId});
                        var tags = self.searchTag.val();
                        self.postCreation(noteId, "NOTE", self.model.get('title'), 1, tags)
                        self.model.set({
                            'createdOn': Math.floor(Date.now()),
                            'modifiedAt': Math.floor(Date.now()),
                            'summary': self.model.get('note').replace(/<(?:.|\n)*?>/gm, ''),
                            'tags': tags
                        });
                        self.collection.unshift(self.model);
                        var entityList = self.buildEntityList();
                        backboneGlobalObj.trigger('entity:displaylist', entityList);
                    }
                });
            }
        },

        fetchNotes: function(e) {

            var self = this;

            if (e) {
                e.preventDefault();
            }

            if (this.collection.length  == parseInt(entityCountModel.attributes.notes)) {
                var entityList = this.buildEntityList();
                backboneGlobalObj.trigger('entity:displaylist', entityList);
                return;
            }
             
            this.model = new Note();   
            this.model.fetch({data: {offset: this.collection.length, limit : 20} }).complete(function(response){
                if (response.status == 200) {
                    var notes = JSON.parse(response.responseText)['note'];
                    if (notes instanceof Array) {
                        for (var index in notes) {
                            var note = new Note(notes[index])
                            self.collection.push(note);
                        }
                    } else if (notes) {
                        var note = new Note(notes);
                        self.collection.push(note);
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
                    'entityId' : this.collection.models[i].attributes.noteId,
                    'entityTitle' : this.collection.models[i].attributes.title,
                    'entitySummary': summary ? summary.substring(0,100) : summary,
                    'entityType': 'note',
                    'modifiedAt': this.collection.models[i].attributes.modifiedAt,
                };
                entityList.push(entity);
            }
            return entityList;
        }
    });

    window.noteView = new NoteView({ collection : new Notes()});

})(jQuery, window, document);