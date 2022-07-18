import { Author } from '@/types/author';

export type Category = 'question' | 'discussion';

export interface ArticleType {
	id: number;
	title: string;
	author: Author;
	content: string;
	category: Category;
	commentCount: number;
	createdAt: string;
}
