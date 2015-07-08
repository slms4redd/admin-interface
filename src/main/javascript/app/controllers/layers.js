import Ember from 'ember';

export default Ember.ArrayController.extend({
  sortProperties: ['rank'],
  sortAscending: true,

  filteredContent: (function () {
    return this.get('arrangedContent').filter(function(item, index) {
      return !(item.get('isNew'));
    });
  }).property('content.@each.isNew'),

  updateSortOrder: function(indexes) {
    this.beginPropertyChanges();
    this.forEach(function(item) {
      var index = indexes[item.get('id')];
      item.set('rank', index);
    });
    this.endPropertyChanges();
  }
});
