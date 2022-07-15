import { setupWorker } from 'msw';

import { LoginHandler } from '@/mock/index';
import { VoteHandler } from './vote';
import { ArticleHandler } from './article';

export const worker = setupWorker(...LoginHandler, ...VoteHandler, ...ArticleHandler);
