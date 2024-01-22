import { IRecette } from 'app/shared/model/recette.model';
import { IUser } from 'app/shared/model/user.model';

export interface ILike {
  id?: number;
  recette?: IRecette | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<ILike> = {};
