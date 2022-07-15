import { setupWorker } from 'msw';

import { LoginHandler } from '@/mock/index';
import { VoteHandler } from '@/mock/vote';
import { ArticleHandler } from '@/mock/article';

export const worker = setupWorker(...LoginHandler, ...VoteHandler, ...ArticleHandler);
