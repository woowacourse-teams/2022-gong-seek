import { Author } from '@/types/author';

export interface CommentType {
	id: number;
	author: Author;
	content: string;
	createdAt: string;
	isAuthor: boolean;
}
