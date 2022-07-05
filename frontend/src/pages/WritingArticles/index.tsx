import PageLayout from '@/components/layout/PageLayout/PageLayout';
import styled from '@emotion/styled';
import { IoIosArrowDown } from 'react-icons/io';
import ToastUiEditor from './ToastUiEditor/ToastUiEditor';

const Container = styled.div`
	width: 100%;
	height: 100%;
	display: flex;
	justify-content: center;
	align-items: center;
	flex-direction: column;
`;

const Content = styled.div`
	width: 100%;
	margin-top: ${({ theme }) => theme.fonts.SIZE_028};
`;

const SelectorBox = styled.div`
	width: 90%;
	display: flex;
	flex-direction: column;
	gap: ${({ theme }) => theme.fonts.SIZE_010};
`;

const TitleInput = styled.input`
	width: 100%;
	padding: 0.6em 0.8em;
	border-style: none;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

const HashTagInput = styled.input`
	width: 100%;
	padding: 0.6em 0.8em;
	border-style: none;
	background-color: transparent;
	&:focus {
		outline: none;
	}
`;

const CategorySelectorBox = styled.div`
	position: relative;
	display: flex;
	align-items: center;
	width: 100%;
`;

const SelectorButton = styled(IoIosArrowDown)`
	position: absolute;
	border: none;
	font-size: ${({ theme }) => theme.fonts.SIZE_018};
	color: ${({ theme }) => theme.colors.PURPLE_500};
	right: ${({ theme }) => theme.fonts.SIZE_004};
	pointer-events: none;
`;

const CategorySelector = styled.select`
	width: 100%;
	padding: 0.6em 0.8em;
	border-color: transparent;
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	&:focus {
		outline: none;
	}

	&:invalid {
		color: rgb(117, 117, 117);
	}
`;

const SubmitButton = styled.button`
	color: ${({ theme }) => theme.colors.WHITE};
	background-color: ${({ theme }) => theme.colors.PURPLE_500};
	width: 90%;
	padding: ${({ theme }) => theme.fonts.SIZE_004};
	border-radius: ${({ theme }) => theme.fonts.SIZE_010};
	border-color: transparent;
	cursor: pointer;
	margin-top: ${({ theme }) => theme.fonts.SIZE_020};

	&:hover,
	&:active {
		background-color: ${({ theme }) => theme.colors.PURPLE_400};
	}
`;

const WritingArticles = () => (
	<Container>
		<SelectorBox>
			<PageLayout width="100%" height="fit-content">
				<TitleInput type="text" placeholder="제목을 입력해주세요" />
			</PageLayout>
			<PageLayout width="100%" height="fit-content">
				<CategorySelectorBox>
					<CategorySelector name="writing" required>
						<option value="" disabled selected>
							카테고리를 선택해주세요
						</option>
						<option value="에러">에러</option>
						<option value="토론">토론</option>
					</CategorySelector>
					<SelectorButton />
				</CategorySelectorBox>
			</PageLayout>
			<PageLayout width="100%" height="fit-content">
				<HashTagInput type="text" placeholder="해쉬태그를 입력해주세요" />
			</PageLayout>
		</SelectorBox>

		<Content>
			<ToastUiEditor />
		</Content>
		<SubmitButton type="button">등록하기</SubmitButton>
	</Container>
);

export default WritingArticles;
