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

    var NoteView = BaseView.extend({

        el: $('#note-wrapper'),
        createTemplate: $('#note-create-template').html(),

        events : {
            'click #note-submit': 'createNote',
            'click #note-cancel': 'resetValues',
            'click #note-tag-img': 'displayTagSelection',
        },

        initialize: function() {
            this.model = new Note();
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
                        // Add it to collection in future
                        self.model.clear();
                    }
                });
            }
        }
    });

    window.noteView = new NoteView();

})(jQuery, window, document);