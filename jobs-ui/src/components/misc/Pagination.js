import React from 'react'

// Object
// content: ...
// empty: false
// first: true
// last: false
// number: 0
// numberOfElements: 20
// pageable: {sort: {…}, offset: 0, pageSize: 20, pageNumber: 0, unpaged: false, …}
// size: 20
// sort: {sorted: false, unsorted: true, empty: true}
// totalElements: 1000
// totalPages: 50

function Pagination(props) {
  return (
    <div className={props.className}>
      <ul class="pagination">
        <li class="disabled"><a href="#!"><i class="material-icons">chevron_left</i></a></li>
        <li class="active"><a href="#!">1</a></li>
        <li class="waves-effect"><a href="#!"><i class="material-icons">chevron_right</i></a></li>
      </ul>
    </div>
  )
}

export default Pagination