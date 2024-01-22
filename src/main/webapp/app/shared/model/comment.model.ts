import { IRecette } from 'app/shared/model/recette.model';
import { IUser } from 'app/shared/model/user.model';

export interface IComment {
  id?: number;
  comment?: string;
  recette?: IRecette | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IComment> = {};
