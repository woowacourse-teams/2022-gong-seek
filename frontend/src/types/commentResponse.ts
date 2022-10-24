import { AuthorType } from '@/api/article/articleType';

export interface CommentType {
	id: number;
	author: AuthorType;
	content: string;
	createdAt: string;
	isAuthor: boolean;
}

export interface UserComment {
	id: number;
	content: string;
	createdAt: string;
	updatedAt: string;
	articleId: number;
	articleTitle: string;
	category: string;
}

export interface UserCommentResponse {
	comments: UserComment[];
}
