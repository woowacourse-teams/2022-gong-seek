import { Author } from '@/types/author';

export type Category = 'question' | 'discussion';

export interface CommonArticleType {
	id: number;
	title: string;
	author: Author;
	content: string;
	category: Category;
	commentCount: number;
	createdAt: string;
}

export interface ArticleType extends CommonArticleType {
	isAuthor: boolean;
	views: number;
	likeCount: number;
}
