import { IUser } from 'app/shared/model/user.model';
import { IComment } from 'app/shared/model/comment.model';
import { ILike } from 'app/shared/model/like.model';

export interface IRecette {
  id?: number;
  title?: string;
  description?: string;
  user?: IUser | null;
  comments?: IComment[] | null;
  likes?: ILike[] | null;
}

export const defaultValue: Readonly<IRecette> = {};
