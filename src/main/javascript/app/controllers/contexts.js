import Ember from 'ember';

export default Ember.ArrayController.extend({
//   needs: "languages",
  
  sortAscending: true,

  filteredContent: (function () {
    return this.get('arrangedContent').filter(function(item, index) {
      return !(item.get('isNew'));
    });
  }).property('content.@each.isNew')
});
