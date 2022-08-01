import { snackBarState } from '@/store/snackBarState';
import { useRecoilState } from 'recoil'

const useSnackBar = () => {
    const [snackBar, setSnackBar] = useRecoilState(snackBarState);
    
    const showSnackBar = (message: string) => {
        setSnackBar({isOpen: true, message: message});
        setTimeout(() => {
            setSnackBar({isOpen: false, message: message});
        },3000);
    }

    return {showSnackBar};
}

export default useSnackBar