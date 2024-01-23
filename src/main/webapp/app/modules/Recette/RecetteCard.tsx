import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardHeader from '@mui/material/CardHeader';
import { Avatar, CardActions, CardContent, IconButton, IconButtonProps, styled, TextField, Typography } from '@mui/material';
import { red } from '@mui/material/colors';
import FavoriteIcon from '@mui/icons-material/Favorite';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import SendIcon from '@mui/icons-material/Send';
import Collapse from '@mui/material/Collapse';
import CommentsList from 'app/modules/Recette/CommentsList';
import { Button, Input } from 'reactstrap';

interface ExpandMoreProps extends IconButtonProps {
  expand: boolean;
}

const ExpandMore = styled((props: ExpandMoreProps) => {
  const { expand, ...other } = props;
  return <IconButton {...other} />;
})(({ theme, expand }) => ({
  transform: !expand ? 'rotate(0deg)' : 'rotate(180deg)',
  marginLeft: 'auto',
  transition: theme.transitions.create('transform', {
    duration: theme.transitions.duration.shortest,
  }),
}));

export default function RecetteCard({ props }) {
  props.likes != null ? '' : '';
  const [expanded, setExpanded] = useState(false);
  const [isLiked, setIsLiked] = useState();

  const handleExpandClick = () => {
    setExpanded(!expanded);
  };

  return (
    <div>
      <Card sx={{ maxWidth: 700, marginBottom: 2 }}>
        <CardHeader
          avatar={
            <Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
              AN
            </Avatar>
          }
          title={props.title}
        />
        <CardContent>
          <Typography variant="body2" color="text.secondary">
            {props.description}
          </Typography>
        </CardContent>
        <CardActions disableSpacing>
          <IconButton aria-label="add to favorites">
            <FavoriteIcon />
          </IconButton>
          <Typography>1256</Typography>
          <div style={{ marginLeft: 'auto', display: 'flex', alignItems: 'center' }} onClick={handleExpandClick}>
            <Typography style={{ marginRight: '8px' }}>Comments 12</Typography>
            <IconButton onClick={handleExpandClick} aria-expanded={expanded} aria-label="show comments">
              <ExpandMore expand={expanded} onClick={handleExpandClick} aria-expanded={expanded} aria-label="show comments">
                <ExpandMoreIcon />
              </ExpandMore>
            </IconButton>
          </div>
        </CardActions>
        <Collapse in={expanded} timeout="auto" unmountOnExit>
          <CardContent>
            {props.comment?.length ? <CommentsList props={props.comments} /> : <Typography> No Comments yet !</Typography>}

            <br />
            <TextField id="standard-helperText" label="Write your comment here" variant="standard" />
            <Button variant="contained" endIcon={<SendIcon />}>
              Send
            </Button>
          </CardContent>
        </Collapse>
      </Card>
    </div>
  );
}
