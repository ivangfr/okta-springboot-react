import React from 'react'
import moment from 'moment'

function TimesAgo({ className, createDate }) {
  return (
    <span className={className}>{moment(createDate, 'YYYY-MM-DD HH:mm:ssZ').local().fromNow()}</span>
  )
}

export default TimesAgo