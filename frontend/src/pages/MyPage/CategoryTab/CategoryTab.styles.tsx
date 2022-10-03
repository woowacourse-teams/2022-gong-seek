import { theme } from '@/styles/Theme';
import styled from '@emotion/styled';

export const Container = styled.nav`
	display: flex;
	width: 100%;
	height: fit-content;

	gap: ${({ theme }) => theme.size.SIZE_001};

	margin-top: ${({ theme }) => theme.size.SIZE_040};
`;

export const Tab = styled.button<{ isClicked: boolean }>`
	width: ${({ theme }) => theme.size.SIZE_140};
	height: fit-content;
	padding: ${({ theme }) => theme.size.SIZE_006};

	text-align: center;

	background-color: ${(props) => (props.isClicked ? theme.colors.PURPLE_500 : theme.colors.WHITE)};
	color: ${(props) => (props.isClicked ? theme.colors.WHITE : theme.colors.PURPLE_500)};
	border-radius: ${({ theme }) => theme.size.SIZE_004} ${({ theme }) => theme.size.SIZE_004} 0 0;
	border: ${({ theme }) => theme.size.SIZE_001} solid ${({ theme }) => theme.colors.GRAY_500};

	&:hover,
	&:active {
		cursor: pointer;
		background-color: ${({ theme }) => theme.colors.PURPLE_500};
		color: ${({ theme }) => theme.colors.WHITE};
	}
`;
