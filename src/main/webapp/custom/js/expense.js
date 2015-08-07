(function($, window, document){

    "use strict"

    var EXPENSE_URL_ROOT = '/iPersonal/dashboard/expenses';

    var Expense = Base.extend({
    	urlRoot: EXPENSE_URL_ROOT,
        
        initialize: function() {
            this.mandatory = {
                'title': true, 'amount': true, 'description': true, 'date': true
            },
            this.maxLength = {
                'title': 50, 'description': 1000
            },
            this.formAttributes = ['title', 'description', 'amount', 'date', 'categories']
        }

    }); 

    var ExpenseView = BaseView.extend({

        el: $('#expense-wrapper'),
        saveForm: $('#expense-form'),
        tagImage: $('#exp-tag-img'),
        searchTag: $('#expense-tag'),
        categoryDropDown: $('#expense-form').find('[name=categories]'),

        events : {
            'click #exp-submit': 'createExpense',
            'click #exp-cancel': 'resetValues',
            'click #exp-tag-img': 'displayTagSelection',
        },

        initialize: function() {
            this.model = new Expense();
        },

        resetValues: function(e) {

            if (e) {
                e.preventDefault();
            }
            BaseView.prototype.resetValues.apply(this, arguments);
            this.categoryDropDown.val('').trigger('chosen:updated');
        },

        populateCategories: function(expenseCategories) {

            var categories = expenseCategoryModel.getExpenseCategories();
            if (categories) {
                this.categoryDropDown.empty();
                for (var i = 0; i < categories.length; i++) {
                    this.categoryDropDown.append($('<option></option>').attr('value', categories[i]).text(categories[i]));
                }
                this.categoryDropDown.trigger('chosen:updated');
            }
        },

        createExpense: function(e) {

            var self = this;
            e.preventDefault();

            self.model.set({
                title: self.saveForm.find('[name=title]').val(),
                description: self.saveForm.find('[name=description]').val(),
                amount: self.saveForm.find('[name=amount]').val(),
                date: (new Date(self.saveForm.find('[name=date]').val()).getTime() / 1000).toFixed(0),
                categories: self.saveForm.find('[name=categories]').val()
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
                        var expenseId = response.responseText;
                        self.model.set({ expenseId: expenseId });
                        var tags = self.searchTag.val();
                        self.postCreation(expenseId, "EXPENSE", self.model.get('title'), 1, tags, self.model.get('categories'))
                        // Add it to collection in future
                        self.model.clear();
                    }
                });
            }
        }
    });

    window.expensesView = new ExpenseView();

})(jQuery, window, document);