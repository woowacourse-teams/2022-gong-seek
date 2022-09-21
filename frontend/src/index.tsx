import ErrorBoundary from './components/helper/ErrorBoundary';
import ReactDOM from 'react-dom/client';
import { QueryClient, QueryClientProvider } from 'react-query';
import { BrowserRouter } from 'react-router-dom';
import { RecoilRoot } from 'recoil';

import App from '@/App';
import { worker } from '@/mock/browser';
import { theme } from '@/styles/Theme';
import { reset } from '@/styles/reset';
import { ThemeProvider, Global } from '@emotion/react';

// if (process.env.NODE_ENV === 'development') {
// 	worker.start();
// }

//TODO: 로직과 UI의 관심사의 분리 -> 커스텀 훅
// UI끼리의 응집도 증가, 좀 더 선언적인 UI -> CompoundComponent 패턴
// 폴더 분리 구조 domain별로 컴포넌트를 나누기
// map을 써서 순회하는것은 List라는 컴포넌트로 분리하자!
// onClick, onChange안에 inline으로 로직적혀있는것 너무 가독성을 해치는것 같다. + 명령적임

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);

export const queryClient = new QueryClient();

root.render(
	<ThemeProvider theme={theme}>
		<Global styles={reset} />
		<QueryClientProvider client={queryClient}>
			<RecoilRoot>
				<BrowserRouter>
					<ErrorBoundary enable={false}>
						<App />
					</ErrorBoundary>
				</BrowserRouter>
			</RecoilRoot>
		</QueryClientProvider>
	</ThemeProvider>,
);
