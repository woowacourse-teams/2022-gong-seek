import { setupWorker } from 'msw';

import { ArticleHandler } from '@/mock/article';
import { CommentHandler } from '@/mock/comment';
import { LoginHandler } from '@/mock/index';
import { VoteHandler } from '@/mock/vote';

export const worker = setupWorker(
	...LoginHandler,
	...VoteHandler,
	...ArticleHandler,
	...CommentHandler,
);
