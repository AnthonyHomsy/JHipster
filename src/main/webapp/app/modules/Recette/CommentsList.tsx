import React from 'react';
import { Typography } from '@mui/material';

export default function CommentsList(props) {
  return (
    <>
      <Typography paragraph>Comments:</Typography>
      {props.comment.maps(el => (
        <Typography>{el.comment}</Typography>
      ))}
    </>
  );
}
