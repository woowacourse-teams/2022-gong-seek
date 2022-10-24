import { AuthorType } from '@/api/article/articleType';

export interface SingleCommentItemType {
	id: number;
	content: string;
	author: AuthorType;
	isAuthor: boolean;
	createdAt: string;
	updatedAt: string;
}

export interface TotalCommentResponseType {
	comments: SingleCommentItemType[];
}

export interface MyPageCommentItemResponse {
	id: number;
	content: string;
	createdAt: string;
	updatedAt: string;
	articleId: number;
	articleTitle: string;
	category: string;
}

export interface MyPageCommentResponse {
	comments: MyPageCommentItemResponse[];
}

export interface CreateCommentRequestType {
	content: string;
	isAnonymous: boolean;
}
export interface UpdateCommentRequestType {
	content: string;
}
