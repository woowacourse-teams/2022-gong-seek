export type Category = 'question' | 'discussion';

export interface ArticleType {
	id: number;
	title: string;
	authorName: string;
	authorAvatarUrl: string;
	content: string;
	category: Category;
	commentCount: number;
	createdAt: string;
}
