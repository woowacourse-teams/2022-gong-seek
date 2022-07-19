import { Author } from '@/types/author';

export interface CommentType {
	id: number;
	authorName: string;
	authorAvatarUrl: string;
	content: string;
	createdAt: string;
	isAuthor: boolean;
}
