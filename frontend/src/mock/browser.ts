import { setupWorker } from 'msw';

import { LoginHandler } from '@/mock/index';
import { VoteHandler } from '@/mock/vote';
import { ArticleHandler } from '@/mock/article';
import { CommentHandler } from '@/mock/comment';

export const worker = setupWorker(
	...LoginHandler,
	...VoteHandler,
	...ArticleHandler,
	...CommentHandler,
);
