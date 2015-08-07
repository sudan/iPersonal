(function($, window, document){

    "use strict"

    var PIN_URL_ROOT = '/iPersonal/dashboard/pins';

    var Pin = Base.extend({
    	urlRoot: PIN_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'name': true, 'imageUrl': true, 'description': true
            },
            this.maxLength = {
                'name': 50, 'imageUrl': 150, 'description': 1000
            },
            this.formAttributes = ['name', 'imageUrl', 'description']
        }

    }); 

    var PinView = BaseView.extend({

        el: $('#pin-wrapper'),
        saveForm: $('#pin-form'),
        tagImage: $('#pin-tag-img'),
        searchTag: $('#pin-tag'),

        events : {
            'click #pin-submit': 'createPin',
            'click #pin-cancel': 'resetValues',
            'click #pin-tag-img': 'displayTagSelection',
        },

        initialize: function() {
            this.model = new Pin();
        },

        createPin: function(e) {

            var self = this;
            e.preventDefault();

            self.model.set({
                name: self.saveForm.find('[name=name]').val(),
                imageUrl: self.saveForm.find('[name=imageUrl]').val(),
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
                        var pinId = response.responseText;
                        self.model.set({ pinId: pinId});
                        var tags = self.searchTag.val();
                        self.postCreation(pinId, "PIN", self.model.get('name'), 1, tags)
                        // Add it to collection in future
                        self.model.clear();
                    }
                });
            }
        }
    });

    window.pinView = new PinView();

})(jQuery, window, document);